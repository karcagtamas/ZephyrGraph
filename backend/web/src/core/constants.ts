import { UserConfig } from 'monaco-editor-wrapper';
import getConfigurationServiceOverride from '@codingame/monaco-vscode-configuration-service-override';
import getTextmateServiceOverride from '@codingame/monaco-vscode-textmate-service-override';
import getThemeServiceOverride from '@codingame/monaco-vscode-theme-service-override';
import { URI } from 'vscode/vscode/vs/base/common/uri';

const LANGUAGE_SERVER = import.meta.env.VITE_LANGUAGE_SERVER;

export const BASE_CONFIG: UserConfig = {
  wrapperConfig: {
    serviceConfig: {
      debugLogging: true,
      workspaceConfig: {
        workspaceProvider: {
          trusted: true,
          workspace: {
            workspaceUri: URI.file('C:\\Users\\karcagtamas\\workspace'),
          },
          async open() {
            return false;
          },
        },
      },
      userServices: {
        ...getConfigurationServiceOverride(),
        ...getTextmateServiceOverride(),
        ...getThemeServiceOverride(),
      },
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
          uri: 'C:\\Users\\karcagtamas\\workspace\\file.kts',
        },
      },
    },
  },
  loggerConfig: {
    enabled: true,
    debugEnabled: true,
  },
  languageClientConfig: {
    languageId: 'kotlin',
    name: 'Kotlin Language Server',
    clientOptions: {
      documentSelector: ['kotlin'],
      workspaceFolder: {
        index: 0,
        name: 'workspace',
        uri: 'C:\\Users\\karcagtamas\\workspace',
      },
    },
    options: {
      $type: 'WebSocketUrl',
      url: LANGUAGE_SERVER,
      stopOptions: {
        onCall: () => {
          console.log('Disconnected from socket.');
        },
        reportStatus: false,
      },
      startOptions: {
        onCall: () => {
          console.log('Connected to socket.');
        },
        reportStatus: false,
      },
    },
  },
};
