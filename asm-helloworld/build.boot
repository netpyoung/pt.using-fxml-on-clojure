(def project 'asm-helloworld)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src"}
          :source-paths   #{"test"}
          :dependencies   '[[org.clojure/clojure "RELEASE"]
                            [adzerk/boot-test "RELEASE" :scope "test"]])

(set-env!
 :dependencies
 '[
   [org.ow2.asm/asm "6.0"]
   [org.ow2.asm/asm-util "6.0"]
])

(task-options!
 aot {:namespace   #{'asm-helloworld.core}}
 pom {:project     project
      :version     version
      :description "FIXME: write description"
      :url         "http://example/FIXME"
      :scm         {:url "https://github.com/yourname/asm-helloworld"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}}
 repl {:init-ns    'asm-helloworld.core}
 jar {:main        'asm-helloworld.core
      :file        (str "asm-helloworld-" version "-standalone.jar")})

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (aot) (pom) (uber) (jar) (target :dir dir))))

(deftask run
  "Run the project."
  [a args ARG [str] "the arguments for the application."]
  (with-pass-thru fs
    (require '[asm-helloworld.core :as app])
    (apply (resolve 'app/-main) args)))

(require '[adzerk.boot-test :refer [test]])
