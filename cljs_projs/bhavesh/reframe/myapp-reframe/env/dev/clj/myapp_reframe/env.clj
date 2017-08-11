(ns myapp-reframe.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [myapp-reframe.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[myapp-reframe started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[myapp-reframe has shut down successfully]=-"))
   :middleware wrap-dev})
