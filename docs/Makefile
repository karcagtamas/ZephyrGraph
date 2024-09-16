BASE := thesis
SUFFIXES := .aux .bbl .bcf .blg .idx .loa .lof .lot .log .lol .nlg .nlo .nls .toc .run.xml .pdf .synctex.gz .ind .ilg .fls .fdb_latexmk
FILES := $(foreach suffix, $(SUFFIXES), $(BASE)$(suffix))

convert:
	pdflatex thesis.tex
	biber thesis
	makeindex -s nomencl.ist -t thesis.nlg -o thesis.nls thesis.nlo
	pdflatex thesis.tex

clear-win:
	@del $(FILES)

clear-lin:
	@rm $(FILES)