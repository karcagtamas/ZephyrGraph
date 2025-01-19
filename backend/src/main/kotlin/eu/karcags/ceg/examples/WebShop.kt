package eu.karcags.ceg.examples

import eu.karcags.ceg.graphmodel.Graph
import eu.karcags.ceg.graphmodel.dsl.*

class WebShop : Example {
    override fun key(): String = "webshop"

    override fun graph(): Graph = graph {
        variables {
            int("price")
            boolean("vip")
            boolean("prepay")
            int("reduction")
        }

        cause("CPRICE200") { variable("price") gte 200 }
        cause("CPRICE100") { variable("price") isIn 100..<200 }
        cause("CPRICE0") { variable("price") lt 100 }
        cause("CVIPFALSE") { variable("vip") eq false }
        cause("CPREPAYFALSE") { variable("prepay") eq false }

        rule {
            and {
                causeById("CPRICE200")
                causeById("CVIPFALSE")
                causeById("CPREPAYFALSE")
            }
            effect { "reduction is 9" }
        }

        rule {
            and {
                causeById("CPRICE200")
                or {
                    and {
                        not { causeById("CVIPFALSE") }
                        causeById("CPREPAYFALSE")
                    }
                    and {
                        causeById("CVIPFALSE")
                        not { causeById("CPREPAYFALSE") }
                    }
                }

            }

            effect { "reduction is 11" }
        }

        rule {
            and {
                causeById("CPRICE200")
                not { causeById("CVIPFALSE") }
                not { causeById("CPREPAYFALSE") }
            }

            effect { "reduction is 14" }
        }

        rule {
            and {
                causeById("CPRICE100")
                causeById("CVIPFALSE")
                causeById("CPREPAYFALSE")
            }
            effect { "reduction is 5" }
        }

        rule {
            and {
                causeById("CPRICE100")
                or {
                    and {
                        not { causeById("CVIPFALSE") }
                        causeById("CPREPAYFALSE")
                    }
                    and {
                        causeById("CVIPFALSE")
                        not { causeById("CPREPAYFALSE") }
                    }
                }

            }

            effect { "reduction is 7" }
        }

        rule {
            and {
                causeById("CPRICE0")
                not { causeById("CVIPFALSE") }
                not { causeById("CPREPAYFALSE") }
            }

            effect { "reduction is 10" }
        }

        rule {
            and {
                causeById("CPRICE100")
                causeById("CVIPFALSE")
                causeById("CPREPAYFALSE")
            }
            effect { "reduction is 1" }
        }

        rule {
            and {
                causeById("CPRICE0")
                or {
                    and {
                        not { causeById("CVIPFALSE") }
                        causeById("CPREPAYFALSE")
                    }
                    and {
                        causeById("CVIPFALSE")
                        not { causeById("CPREPAYFALSE") }
                    }
                }

            }

            effect { "reduction is 4" }
        }

        rule {
            and {
                causeById("CPRICE0")
                not { causeById("CVIPFALSE") }
                not { causeById("CPREPAYFALSE") }
            }

            effect { "reduction is 6" }
        }
    }
}