(ns ActionRepository)

(def actions
  {::get #{:open :access}
   ::click #{:click :press}
   ::select #{:select :choose}
   ::sendKey #{:enter :fill}
   ::getName #{:verify :check}
   ::execute #{:execute :perform :create :run}
   ::login #{:login}})


