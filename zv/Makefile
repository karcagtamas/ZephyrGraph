BASE := zv
SUFFIXES := .aux .log .toc .run.xml .pdf .synctex.gz .fdb_latexmk .nav .out .snm .fls
FILES := $(foreach suffix, $(SUFFIXES), $(BASE)$(suffix))

convert:
	pdflatex zv.tex

clear-win:
	@del $(FILES)

clear-lin:
	@rm $(FILES)