(ns markov.core
  (:require [markov.tokenizer :as t]
            [clojure.string :as str]))

(def corpus ["Pretty sure I just spotted a blond @sheley."
             "Shout out to all of you who don't fear learning!"
             "@headius @plexus yeah. it's just an example, but pretty much any pattern matching would be better than out primitive case :P"
             "@plexus @headius get matz to backwards-compatibly revamp \"case\" for pattern matching a la fuby!!!!! PRIORITY"
             "@arsduo @sandimetz hahhaha noooooo"
             "@eljojo @malweene it is a serious concert"
             "@malweene @eljojo \"shit you found out! it was a surprise for you guys!!!!!!!!!!\""
             "@arsduo I'm jelly!"
             "And what naturally happens when I want to write a blogpost about Clojure is that first I end up rewriting my blog in Clojure. *sigh*"
             "@lucapette I should write a short blogpost. It's a few things that happened in the very few days I've been coding in it. /cc @brixen"
             "Had to literally force myself to go home after such a long work day. Clojure is so addictive!"
             "OH: \"type systems are no picnic\""
             "@mperham that sounds terrible."
             "My first piece of Rust code that COMPILES (and passes tests)!"
             "Finally, the Rust build process includes building the documentation automatically! Neat."])
