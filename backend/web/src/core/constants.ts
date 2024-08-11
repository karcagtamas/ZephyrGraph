import { UserConfig } from 'monaco-editor-wrapper';

export const TOKENS = {
  keywords: [
    'as',
    'break',
    'class',
    'continue',
    'do',
    'else',
    'false',
    'for',
    'fun',
    'if',
    'in',
    'interface',
    'is',
    'null',
    'object',
    'package',
    'return',
    'super',
    'this',
    'throw',
    'true',
    'try',
    'typealias',
    'typeof',
    'val',
    'var',
    'when',
    'while',
  ],
  typeKeywords: [
    'Int',
    'Float',
    'Double',
    'Boolean',
    'String',
    'Any',
    'Unit',
    'Nothing',
    'Long',
    'Short',
    'Byte',
    'Char',
  ],
  operators: [
    '=',
    '>',
    '<',
    '!',
    '~',
    '?',
    '::',
    '?:',
    '?.',
    '?:',
    ':',
    '==',
    '!=',
    '<=',
    '>=',
    '!!',
    '++',
    '--',
    '&&',
    '||',
    '+=',
    '-=',
    '*=',
    '/=',
    '&=',
    '|=',
    '^=',
    '%=',
    '<<=',
    '>>=',
    '>>>=',
  ],
  symbols: /[=><!~?:&|+\-*/^%]+/,
  escapes: /\\(?:[abtnvfr"'\\]|u[0-9A-Fa-f]{4})/,
  tokenizer: {
    root: [
      [
        /\b(?:[a-z_$][\w$]*|`[\w\s]*`)\b/,
        { cases: { '@keywords': 'keyword', '@default': 'identifier' } },
      ],
      { include: '@whitespace' },
      [/[{}()[\]]/, '@brackets'],
      [/@symbols/, { cases: { '@operators': 'operator', '@default': '' } }],
      [/\d*\.\d+([eE][-+]?\d+)?[fF]?/, 'number.float'],
      [/\d+[lL]?/, 'number'],
      [/"([^"\\]|\\.)*$/, 'string.invalid'],
      [/"/, { token: 'string.quote', bracket: '@open', next: '@string' }],
      [/'[^\\']'/, 'string'],
      [/'/, 'string.invalid'],
    ],
    whitespace: [
      [/[ \t\r\n]+/, ''],
      [/\/\*/, 'comment', '@comment'],
      [/\/\/.*$/, 'comment'],
    ],
    comment: [
      [/[^/*]+/, 'comment'],
      [/\*\//, 'comment', '@pop'],
      [/[/*]/, 'comment'],
    ],
    string: [
      [/[^\\"]+/, 'string'],
      [/@escapes/, 'string.escape'],
      [/\\./, 'string.escape.invalid'],
      [/"/, { token: 'string.quote', bracket: '@close', next: '@pop' }],
    ],
  },
};

export const BASE_CONFIG: UserConfig = {
  wrapperConfig: {
    serviceConfig: {
      debugLogging: true,
    },
    editorAppConfig: {
      $type: 'classic',
      useDiffEditor: false,
      languageDef: {
        monarchLanguage: {
          tokenizer: {
            root: [
              // Keywords
              [
                /\b(?:as|break|class|continue|do|else|false|for|fun|if|in|interface|is|null|object|package|return|super|this|throw|true|try|typealias|typeof|val|var|when|while)\b/,
                'keyword',
              ],

              // Type keywords
              [
                /\b(?:Int|Float|Double|Boolean|String|Any|Unit|Nothing|Long|Short|Byte|Char)\b/,
                'type',
              ],

              // Operators
              [/[=><!~?:&|+\-*/^%]+/, 'operator'],

              // Symbols (e.g., brackets, parentheses)
              [/[{}()[\]]/, '@brackets'],

              // Numbers
              [/\d*\.\d+([eE][-+]?\d+)?[fF]?/, 'number.float'],
              [/\d+[lL]?/, 'number'],

              // String literals
              [
                /"/,
                { token: 'string.quote', bracket: '@open', next: '@string' },
              ],
              [/'[^\\']'/, 'string'],
              [/'/, 'string.invalid'],

              // Comments
              [/\/\/.*$/, 'comment'],
              [/\/\*/, 'comment', '@comment'],
            ],
            comment: [
              [/[^/*]+/, 'comment'],
              [/\*\//, 'comment', '@pop'],
              [/[/*]/, 'comment'],
            ],
            string: [
              [/[^\\"]+/, 'string'],
              [/\\./, 'string.escape'],
              [/"/, { token: 'string.quote', bracket: '@close', next: '@pop' }],
            ],
          },
          ignoreCase: false,
          unicode: true,
          defaultToken: 'source',
          brackets: [
            { open: '{', close: '}', token: 'delimiter.curly' },
            { open: '[', close: ']', token: 'delimiter.bracket' },
            { open: '(', close: ')', token: 'delimiter.parenthesis' },
          ],
          start: 'root',
          tokenPostfix: '.kotlin',
          includeLF: false,
        },
        languageExtensionConfig: {
          id: 'kotlin',
          extensions: ['.kts', '.kt'],
          aliases: ['KOTLIN', 'kotlin'],
          mimetypes: ['text/x-kotlin-source', 'text/x-kotlin'],
        },
      },
      editorOptions: {
        fontFamily: "'JetBrains Mono', monospace",
        fontSize: 14,
        minimap: {
          enabled: true,
        },
        scrollBeyondLastLine: false,
        tabSize: 2,
        padding: {
          top: 15,
        },
        lineNumbersMinChars: 1,
      },
      codeResources: {
        main: {
          text: '//comment',
          fileExt: 'kts',
        },
      },
    },
  },
  languageClientConfig: {
    languageId: 'kotlin',
    options: {
      $type: 'WebSocketUrl',
      url: 'ws://localhost:8080/ws/language-server/kotlin',
      stopOptions: {
        onCall: () => {
          console.log('Disconnected from socket.');
        },
        reportStatus: true,
      },
      startOptions: {
        onCall: () => {
          console.log('Connected to socket.');
        },
        reportStatus: true,
      },
    },
  },
};
