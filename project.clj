(defproject smugglers-cantina "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.597"
                  :exclusions [com.google.javascript/closure-compiler-unshaded
                               org.clojure/google-closure-library
                               org.clojure/google-closure-library-third-party]]
                 [thheller/shadow-cljs "2.8.83"]
                 [re-frame "0.12.0"]
                 [reagent "0.10.0"]
                 [day8.re-frame/http-fx "v0.2.0"]
                 [secretary "1.2.3"]
                 [garden "1.3.9"]
                 [ns-tracker "0.4.0"]

                 [amazonica "0.3.152"]]

  :plugins [[lein-garden "0.3.0"]
            [lein-shell "0.5.0"]]

  :min-lein-version "2.5.3"

  :jvm-opts ["-Xmx1G"]

  :source-paths ["src/clj" "src/cljs"]

  :test-paths   ["test/cljs"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"
                                    "test/js"
                                    "resources/public/css"]


  :garden {:builds [{:id           "screen"
                     :source-paths ["src/clj"]
                     :stylesheet   smugglers-cantina.css/screen
                     :compiler     {:output-to     "resources/public/css/screen.css"
                                    :pretty-print? true}}]}

  :shell {:commands {"open" {:windows ["cmd" "/c" "start"]
                             :macosx  "open"
                             :linux   "xdg-open"}}}

  :aliases {"dev"          ["with-profile" "dev" "do"
                            ["run" "-m" "shadow.cljs.devtools.cli" "watch" "app"]]
            "prod"         ["with-profile" "prod" "do"
                            ["run" "-m" "shadow.cljs.devtools.cli" "release" "app"]]
            "deploy-local" ["with-profile" "dev" "do"
                            ["shell" "sam" "deploy"
                             "--stack-name" "smugglers-cantina-local"
                             "--capabilities" "CAPABILITY_IAM"
                             "--parameter-overrides" "Domain=http://localhost:8280 AuthDomain=smugglers-cantina-local"
                             "--no-fail-on-empty-changeset"]
                            ["run" "-m" "dev.env" "smugglers-cantina-local" "us-east-1"]]
            "deploy-prod"  ["with-profile" "prod" "do"
                            ["shell" "sam" "deploy"
                             "--stack-name" "smugglers-cantina"
                             "--capabilities" "CAPABILITY_IAM"
                             "--parameter-overrides" "Domain=https://smugglers-cantina.com AuthDomain=smugglers-cantina"
                             "--no-fail-on-empty-changeset"]
                            ["run" "-m" "dev.env" "smugglers-cantina" "us-east-1"]
                            ["run" "-m" "shadow.cljs.devtools.cli" "release" "app"]
                            ["shell" "aws" "s3" "cp" "resources/public" "s3://smugglers-cantina.com/" "--recursive" "--exclude" "js/compiled/cljs-runtime/*"]
                            ["shell" "aws" "cloudfront" "create-invalidation" "--distribution-id" "E1GQSPKKIZI9H1" "--paths" "/js/compiled/app.js"]]
            "build-report" ["with-profile" "prod" "do"
                            ["run" "-m" "shadow.cljs.devtools.cli" "run" "shadow.cljs.build-report" "app" "target/build-report.html"]
                            ["shell" "open" "target/build-report.html"]]
            "karma"        ["with-profile" "prod" "do"
                            ["run" "-m" "shadow.cljs.devtools.cli" "compile" "karma-test"]
                            ["shell" "karma" "start" "--single-run" "--reporters" "junit,dots"]]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "1.0.0"]
                   [day8.re-frame/re-frame-10x "0.6.0"]
                   [day8.re-frame/tracing "0.5.3"]]
    :source-paths ["dev"]}

   :prod { :dependencies [[day8.re-frame/tracing-stubs "0.5.3"]]}}

  :prep-tasks [["garden" "once"]])
