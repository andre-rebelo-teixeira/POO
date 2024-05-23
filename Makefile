# Define variables
JAVAC = javac
JAVA = java
OUT_DIR = out

# Directories to include
SRC_DIRS = ExponentialDistribution Main Pair population Event

# Find all .java source files in the specified directories
SOURCES := $(shell find $(SRC_DIRS) -name "*.java")

# Create a list of .class files to be generated
CLASSES := $(SOURCES:%.java=$(OUT_DIR)/%.class)

# Default target
all: $(CLASSES)

# Compile .java files to .class files
$(OUT_DIR)/%.class: %.java
	@mkdir -p $(dir $@)
	$(JAVAC) -d $(OUT_DIR) $<

# Run the main class
run: all
	$(JAVA) -cp $(OUT_DIR) Main.Main

# Clean the build directory
clean:
	rm -rf $(OUT_DIR)

# PHONY targets
.PHONY: all run clean

