# Variables
JAVAC=javac
JAR=jar
SRC_DIRS=CustomEvents Event ExponentialDistribution Main Pair population SimulationData SimulationHandler
BIN_DIR=out
MANIFEST=manifest.txt
MAIN_CLASS=Main.Main

# Targets
all: clean compile jar

compile:
	@mkdir -p $(BIN_DIR)
	$(foreach dir, $(SRC_DIRS), $(JAVAC) -Xlint -d $(BIN_DIR) $(dir)/*.java;)

jar: compile
	@echo "Main-Class: $(MAIN_CLASS)" > $(MANIFEST)
	@$(JAR) cfm $(BIN_DIR)/yourapp.jar $(MANIFEST) -C $(BIN_DIR) .

clean:
	@rm -rf $(BIN_DIR)/*
	@rm -f $(MANIFEST)

debug: clean
	@mkdir -p $(BIN_DIR)
	$(foreach dir, $(SRC_DIRS), $(JAVAC) -g -d $(BIN_DIR) $(dir)/*.java;)

release: clean
	@mkdir -p $(BIN_DIR)
	$(foreach dir, $(SRC_DIRS), $(JAVAC) -O -d $(BIN_DIR) $(dir)/*.java;)

run: compile
	@java -cp $(BIN_DIR) $(MAIN_CLASS)

.PHONY: all compile jar clean debug release run

