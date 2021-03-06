(ns todo-app.core
  (:require [reagent.core :as r]
            [reagent.session :as session]
            [todo-app.ajax :refer [load-interceptors!]]
            [ajax.core :refer [GET POST]]
            [secretary.core :as secretary :refer-macros [defroute]]
            [accountant.core :as accountant]))

#_(accountant/configure-navigation! {:nav-handler (fn [path]
                                                  (secretary/dispatch! path))
                                   :path-exists? (fn [path]
                                                   (secretary/locate-route path))})


(def status (r/atom {}))

(def user-atom (r/atom nil))

(defn get-by-id [id] (.-value (.getElementById js/document id)))

(defn log [& params] (.log js/console (apply str params)))

(def server "http://localhost:3000/")

(defn error-handler [] (log "error!"))


#_(defroute "/users/:id" [id query-params]
  (.log js/console (str "User :" id))
  (.log js/console (pr-str query-params)))

#_(secretary/dispatch! "/users/gf3?action=delete")



(defn h-update-atom!
  "stores the response from the server into the status atom"
  [response]
  (log response)
  (if (:status response)
    (reset! status (:content response))
    (js/alert "Sorry! Task insertion Failed.")))


(defn mark-done
  "changes the status of a task to complete"
  [task]
  (GET (str server "done")
       {:params {:user @user-atom
                 :task task}
        :format :json
        :response-format :json
        :keywords? true
        :handler h-update-atom!
        :error-handler error-handler}))



(defn show-tasks
  "shows the task, the status, and a button to mark the task complete if it is incomplete"
  [map-task]
  #_(log map-task)
  (let [task (:task map-task)
        status (:status map-task)]
    [:div
     [:li [:input {:type "text" :value task}]
      [:input {:type "text" :value status}]
      (if-not (= "Complete" status)
        [:input {:type "button" :value "Mark done" :on-click #(mark-done task)}])]]))



(defn c-show-output
  [n]
  [:ul
   (doall (map show-tasks @status))])

(defn login-handler
  [flag]
  (if flag
    (do (secretary/dispatch! "/user")
        (GET (str server "tasks")
             {:params {:user @user-atom}
              :format :json
              :response-format :json
              :keywords? true
              :handler h-update-atom!
              :error-handler error-handler}))))

(defn login-error
  [flag]
  nil)



(defn login-page []
  [:div
   [:h2 "Login Page"]
   [:form {:action "#" :method "get"
           :on-submit (fn [e]
                        (let [user (get-by-id "user")
                              pass (get-by-id "pass")]
                          (reset! user-atom user)
                          (GET (str server "user")
                               {:params {:user user
                                         :password pass}
                                :format :json
                                :response-format :json
                                :keywords? true
                                :handler login-handler
                                :error-handler login-error})))}
    [:div [:input {:type "text" :id "user"}]]
    [:div [:input {:type "text" :id "pass"}]]
    [:div [:input {:type "submit" :value "Login"}]]]])


(defn todo-page []
  [:div
   [:h2 "Todo-list"]
   [:form {:action "#" :method "get"
           :on-submit (fn [e]
                        (let [t (get-by-id "todo-input") ]
                          (GET (str server "todo")
                               {:params {:task t
                                         :user @user-atom}
                                :format :json
                                :response-format :json
                                :keywords? true
                                :handler h-update-atom!
                                :error-handler error-handler})))}
    [:div [:input {:type "text"
                   :id "todo-input"}]
     [:input {:type "submit" :value "Add-task"}]
     [:input {:type "button" :value "Logout" :on-click #(secretary/dispatch! "/")}]]
    [:div [:h2 "all tasks"]
     [c-show-output]]]])



(def pages
  {:home login-page
   :todo todo-page})

(session/put! :page :home)

(defn page []
  [(pages (session/get :page))])



(defroute "/user" []
  (session/put! :page :todo))

(defroute "/" []
  (session/put! :page :home))


#_(defn show-page []
  (condp = @cur-page
    "login" [login-page]
    "todo" [todo-page]))

(defn mount-components []
  (r/render [page] (.getElementById js/document "app")))

(defn init! []
  (mount-components))
