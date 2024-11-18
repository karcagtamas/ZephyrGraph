import eu.karcags.ceg.graphmodel.dsl.*

graph {
    variables {
        int("a")
    }

    cause("C1") { variable("a") eq 10 }
    cause("C2") { variable("a") eq 10 }

    rule {
        and {
            or {
                and {
                    or {
                        and {
                            or {
                                and {
                                    or {
                                        and {
                                            or {
                                                and {
                                                    not {
                                                        or {
                                                            causeById("C1")
                                                            and {
                                                                causeById("C1")
                                                                causeById("C2")
                                                            }
                                                        }
                                                    }
                                                    not {
                                                        and {
                                                            or {
                                                                causeById("C1")
                                                                causeById("C2")
                                                            }
                                                            causeById("C2")
                                                        }
                                                    }
                                                }
                                                and {
                                                    not {
                                                        or {
                                                            causeById("C1")
                                                            causeById("C2")
                                                        }
                                                    }
                                                    not {
                                                        and {
                                                            causeById("C1")
                                                            causeById("C2")
                                                        }
                                                    }
                                                }
                                            }
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                    }
                                    or {
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                    }
                                }
                                and {
                                    or {
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                    }
                                    or {
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                    }
                                }
                            }
                            or {
                                and {
                                    or {
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                    }
                                    or {
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                    }
                                }
                                and {
                                    or {
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                    }
                                    or {
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                    }
                                }
                            }
                        }
                        and {
                            or {
                                and {
                                    or {
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                    }
                                    or {
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                    }
                                }
                                and {
                                    or {
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                    }
                                    or {
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                    }
                                }
                            }
                            or {
                                and {
                                    or {
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                    }
                                    or {
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                    }
                                }
                                and {
                                    or {
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                    }
                                    or {
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                        and {
                                            causeById("C1")
                                            causeById("C2")
                                        }
                                    }
                                }
                            }
                        }
                    }
                    or {
                        causeById("C1")
                        causeById("C2")
                    }
                }
                and {
                    or {
                        causeById("C1")
                        causeById("C2")
                    }
                    or {
                        causeById("C1")
                        causeById("C2")
                    }
                }
            }
            or {
                and {
                    or {
                        causeById("C1")
                        causeById("C2")
                    }
                    or {
                        causeById("C1")
                        causeById("C2")
                    }
                }
                and {
                    or {
                        causeById("C1")
                        causeById("C2")
                    }
                    or {
                        causeById("C1")
                        causeById("C2")
                    }
                }
            }
        }

        effect { "Effect" }
    }
}