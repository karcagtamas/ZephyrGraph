import { UserConfig } from 'monaco-editor-wrapper';
import * as vscode from 'vscode';
import { useOpenEditorStub } from 'monaco-editor-wrapper/vscode/services';
import getConfigurationServiceOverride from '@codingame/monaco-vscode-configuration-service-override';
import getModelServiceOverride from '@codingame/monaco-vscode-model-service-override';
import getEditorServiceOverride from '@codingame/monaco-vscode-editor-service-override';

const HOSTNAME =
  import.meta.env.VITE_LANGUAGE_SERVER_HOSTNAME ?? window.location.hostname;
const PORT = import.meta.env.VITE_LANGUAGE_SERVER_PORT ?? window.location.port;
const PATH = import.meta.env.VITE_LANGUAGE_SERVER_PATH;
const IS_SECURE =
  import.meta.env.VITE_LANGUAGE_SERVER_PROTOCOL === undefined
    ? window.location.protocol === 'https:'
    : import.meta.env.VITE_LANGUAGE_SERVER_PROTOCOL === 'wss';

console.log(HOSTNAME, PATH, PORT, IS_SECURE, typeof IS_SECURE, window.location);

import '@codingame/monaco-vscode-theme-defaults-default-extension';

export const createUserConfig = (
  workspace: string,
  code: string,
  codeUri: string
): UserConfig => {
  return {
    id: 'kotlin',
    languageClientConfig: {
      languageId: 'kotlin',
      name: 'Kotlin Language Server',
      options: {
        $type: 'WebSocket',
        host: HOSTNAME,
        port: PORT,
        path: PATH,
        extraParams: {
          authorization: 'UserAuth',
        },
        secured: IS_SECURE,
      },
      clientOptions: {
        documentSelector: ['kotlin'],
        workspaceFolder: {
          index: 0,
          name: 'workspace',
          uri: vscode.Uri.parse(workspace),
        },
      },
    },
    wrapperConfig: {
      serviceConfig: {
        userServices: {
          ...getEditorServiceOverride(useOpenEditorStub),
          ...getConfigurationServiceOverride(vscode.Uri.file(workspace)),
          //...getTextmateServiceOverride(),
          //...getKeybindingsServiceOverride(),
          //...getMonarchServiceOverride(),
          //...getLanguagesServiceOverride(),
          ...getModelServiceOverride(),
          //...getThemeServiceOverride(),
        },
        debugLogging: true,
      },
      editorAppConfig: {
        $type: 'classic',
        codeResources: {
          main: {
            text: code,
            uri: codeUri,
          },
        },
        editorOptions: {
          fontFamily: "'JetBrains Mono', monospace",
          fontSize: 13,
          minimap: {
            enabled: true,
          },
          scrollBeyondLastLine: false,
          tabSize: 2,
          padding: {
            top: 14,
          },
          lineNumbersMinChars: 1,
          theme: 'vs',
          fontLigatures: true,
          contextmenu: false,
          insertSpaces: true,
          detectIndentation: false,
        },
        useDiffEditor: false,
        languageDef: {
          monarchLanguage: {
            tokenizer: {
              root: [
                // Keywords
                [
                  /\b(?:as|break|class|continue|do|else|false|for|fun|if|in|interface|is|null|object|package|return|super|this|throw|true|try|typealias|typeof|val|var|when|while|import)\b/,
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
                [
                  /"/,
                  { token: 'string.quote', bracket: '@close', next: '@pop' },
                ],
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
          },
          languageExtensionConfig: {
            id: 'kotlin',
            extensions: ['.kts', '.kt'],
            aliases: ['KOTLIN', 'kotlin'],
            mimetypes: ['text/x-kotlin-source', 'text/x-kotlin'],
          },
        },
      },
    },
    loggerConfig: {
      enabled: true,
      debugEnabled: true,
    },
  };
};
