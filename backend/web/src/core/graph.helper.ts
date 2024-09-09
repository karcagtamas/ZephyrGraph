// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const getSuggestions = (monaco: any): any => {
  return [
    {
      label: 'graph',
      kind: monaco.languages.CompletionItemKind.Function,
      insertText: 'graph {\n\t${1}\n}',
      insertTextRules:
        monaco.languages.CompletionItemInsertTextRule.InsertAsSnippet,
      documentation: 'Graph base function',
    },
    {
      label: 'rule',
      kind: monaco.languages.CompletionItemKind.Function,
      insertText: 'rule {\n\t${1}\n}',
      insertTextRules:
        monaco.languages.CompletionItemInsertTextRule.InsertAsSnippet,
      documentation: 'Rule function',
    },
    {
      label: 'source',
      kind: monaco.languages.CompletionItemKind.Keyword,
      insertText: 'source = ${1}',
      insertTextRules:
        monaco.languages.CompletionItemInsertTextRule.InsertAsSnippet,
      documentation: 'Rule source definition',
    },
    {
      label: 'target',
      kind: monaco.languages.CompletionItemKind.Keyword,
      insertText: 'target = ${1}',
      insertTextRules:
        monaco.languages.CompletionItemInsertTextRule.InsertAsSnippet,
      documentation: 'Rule target definition',
    },
    {
      label: 'cause',
      kind: monaco.languages.CompletionItemKind.Function,
      insertText:
        'cause {\n\tdisplayName = "${1}"\n\n\texpression { "${2}" }\n}',
      insertTextRules:
        monaco.languages.CompletionItemInsertTextRule.InsertAsSnippet,
      documentation: 'Cause function',
    },
    {
      label: 'displayName',
      kind: monaco.languages.CompletionItemKind.Keyword,
      insertText: 'displayName = ${1}',
      insertTextRules:
        monaco.languages.CompletionItemInsertTextRule.InsertAsSnippet,
      documentation: 'Display name definition',
    },
    {
      label: 'description',
      kind: monaco.languages.CompletionItemKind.Keyword,
      insertText: 'description = ${1}',
      insertTextRules:
        monaco.languages.CompletionItemInsertTextRule.InsertAsSnippet,
      documentation: 'Description definition',
    },
    {
      label: 'expression',
      kind: monaco.languages.CompletionItemKind.Function,
      insertText: 'expression { "${1}" }',
      insertTextRules:
        monaco.languages.CompletionItemInsertTextRule.InsertAsSnippet,
      documentation: 'Cause expression function',
    },
    {
      label: 'effect',
      kind: monaco.languages.CompletionItemKind.Function,
      insertText:
        'effect {\n\tdisplayName = "${1}"\n\n\tstatement { "${2}" }\n}',
      insertTextRules:
        monaco.languages.CompletionItemInsertTextRule.InsertAsSnippet,
      documentation: 'Effect function',
    },
    {
      label: 'statement',
      kind: monaco.languages.CompletionItemKind.Function,
      insertText: 'statement { "${1}" }',
      insertTextRules:
        monaco.languages.CompletionItemInsertTextRule.InsertAsSnippet,
      documentation: 'Effect statement function',
    },
  ];
};
