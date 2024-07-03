# Thesis

- This is my Thesis repository what contains the codebase, notes and documentation

## Notes

The idea for the cause-effect diagram is a `YAML` structure based scriptic language

```yaml
cause_effect_diagram:
  effect: Low Product Quality
  categories:
    People:
      - Insufficient Training
      - Lack of Motivation
    Methods:
      - Inadequate Procedures
      - Poor Process Design
    Machines:
      - Equipment Malfunctions
      - Outdated Technology
    Materials:
      - Poor Quality Raw Materials
      - Inconsistent Suppliers
    Measurements:
      - Inaccurate Measurements
      - Infrequent Inspections
    Environment:
      - Poor Working Conditions
      - Extreme Temperatures
```

- Effect details
- Categories
  - Each entry is a category
    - It has a meta data
    - It has sub-categories (causes)
- Sub-categories
  - It has meta data
  - It can have additional sub-categories
- What is the meta-data for each?

### Platform

- Basic web client:
  - In React
- A computing web server
  - Kotlin or C#

## Tasks

- [ ] Write a parser for the scriptic language
- [ ] Validate the structure of the diagram data
- [ ] Dispaly the fishbone diagram from the data
- [ ] Convert this data
