type SubBlock = {
  text: string;
  code: string;
};

type Block = {
  caption: string;
  text: string;
  code: string;
  children: SubBlock[];
};

export const source: Block[] = [
  {
    caption: 'Import',
    text: 'The script must begin with the following import as the first row. Without this, the script structure element will be unavailable, causing the execution to fail.',
    code: 'import eu.karcags.ceg.graphmodel.dsl.*',
    children: [],
  },
  {
    caption: 'Graph',
    text: "To define a graph, include the 'graph' clause as the last element in the Kotlin file. This clause will contain the rule definitions and expressions.",
    code: 'graph {\n\n}',
    children: [],
  },
  {
    caption: 'Variables',
    text: "Variables must be defined within the 'variables' block inside the graph. In this block, you can define three types of variables: Int, Boolean, and Float.",
    code: 'graph {\n\tvariables {\n\t\tint("a")\n\t\tfloat("b")\n\t\tboolean("c")\n\t}\n}',
    children: [
      {
        text: 'Only the predefined variables can be used in cause expressions. These variables can be reused with variable("variable_name")',
        code: 'variable("a")',
      },
    ],
  },
  {
    caption: 'Rule',
    text: 'Each rule defines a cause and an effect. The cause contains expressions, while the effect provides a description of the outcome. These are essential components of the rule. Each cause must have a unique string key, such as "C1."',
    code: 'graph {\n\tvariables {\n\t\tint("a")\n\t}\n\n\trule {\n\t\tcause("C1") { variable("a") eq 10 }\n\t\teffect { "Description of the effect" }\n\t}\n}',
    children: [
      {
        text: "Causes can be defined at the graph level, allowing them to be reused across different rules and expressions. To enable this, define each cause using the 'cause' clause within the 'graph' clause, and reference it with the 'causeById' function. Use the same unique key for referencing.",
        code: 'graph {\n\tvariables {\n\t\tint("a")\n\t}\n\n\tcause("C1") { variable("a") eq 10 }\n\n\trule {\n\t\tcauseById("C1")\n\t\teffect { "Description of the effect" }\n\t}\n}',
      },
      {
        text: "Causes can also incorporate complex logic. You can wrap causes in logical operators like 'and', 'or', or 'not'. These clauses can be nested indefinitely within each other, but ultimately, each structure must end with a cause. The 'and' and 'or' clauses must contain at least two child elements, while the 'not' clause should contain only one.",
        code: 'graph {\n\tvariables {\n\t\tint("a")\n\t}\n\n\trule {\n\t\tand {\n\t\t\tor {\n\t\t\t\tcause("C1") { variable("a") eq 10 }\n\t\t\t\tcause("C2") { variable("a") lte 12 }\n\t\t\t}\n\t\t\tnot { cause("C3") { variable("a") gt 34 } }\n\t\t}\n\t\teffect { "Description of the effect" }\n\t}\n}',
      },
    ],
  },
  {
    caption: 'Expressions',
    text: 'Each cause contains an expression, which is a logical definition. On the left side is a variable, and on the right side is a literal. A logical operator connects them. Literals can be Int, Float, or Boolean values and must match the type of the variable.',
    code: 'graph {\n\tvariables {\n\t\tboolean("a")\n\t}\n\n\tcause("C1") { variable("a") eq 10.2f }\n\n\trule {\n\t\tcauseById("C1")\n\t\teffect { "Description of the effect" }\n\t}\n}',
    children: [
      {
        text: '',
        code: 'graph {\n\tvariables {\n\t\tfloat("a", 1.2f) // with precision\n\t}\n\n\trule {\n\t\tcause("C1") { variable("a") eq false }\n\t\teffect { "Description of the effect" }\n\t}\n}',
      },
    ],
  },
];

type LogicalOperator = {
  operator: string;
  description: string;
  types: string[];
  example: string;
};

export const logicalOperators: LogicalOperator[] = [
  {
    operator: 'eq',
    description: 'Equals',
    types: ['Int', 'Float', 'Boolean'],
    example: 'variable("a") eq 100',
  },
  {
    operator: 'neq',
    description: 'Not Equals',
    types: ['Int', 'Float', 'Boolean'],
    example: 'variable("a") neq 100',
  },
  {
    operator: 'lt',
    description: 'Lower Than',
    types: ['Int', 'Float'],
    example: 'variable("a") lt 100',
  },
  {
    operator: 'lte',
    description: 'Lower Than Or Equals',
    types: ['Int', 'Float'],
    example: 'variable("a") lte 100',
  },
  {
    operator: 'gt',
    description: 'Greater Than',
    types: ['Int', 'Float'],
    example: 'variable("a") gt 100',
  },
  {
    operator: 'gte',
    description: 'Greater Than Or Equals',
    types: ['Int', 'Float'],
    example: 'variable("a") gte 100',
  },
  {
    operator: 'isIn',
    description: 'Is value in the interval',
    types: ['Int', 'Float'],
    example: 'variable("a") isIn 1..4',
  },
  {
    operator: 'isNotIn',
    description: 'Is value not in the interval',
    types: ['Int', 'Float'],
    example: 'variable("a") isIn 1f..<42.f',
  },
];
