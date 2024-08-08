import { FC } from 'react';
import MonacoEditor, { OnMount } from '@monaco-editor/react';
import './Editor.scss';
import { getSuggestions } from '../../core/graph.helper';
import { initServices } from 'monaco-languageclient/vscode/services';
import * as MONACO from 'monaco-editor';
import { connectToLs } from '../../core/language-server';

// eslint-disable-next-line react-refresh/only-export-components
export const MONACO_OPTIONS: MONACO.editor.IEditorConstructionOptions = {
  autoIndent: 'full',
  automaticLayout: true,
  contextmenu: true,
  fontFamily: 'monospace',
  fontSize: 14,
  lineHeight: 24,
  hideCursorInOverviewRuler: true,
  matchBrackets: 'always',
  minimap: {
    enabled: false,
  },
  readOnly: false,
  scrollbar: {
    horizontalSliderSize: 4,
    verticalSliderSize: 18,
  },
};

type Props = {
  onChange: (value: string) => void;
  language: string;
  value: string | undefined;
};

const Editor: FC<Props> = (props: Props) => {
  const handleChange = (value: string | undefined) => {
    if (value) {
      props.onChange(value);
    }
  };

  const handleMount: OnMount = (editor, monaco) => {
    if (monaco) {
      monaco.languages.registerCompletionItemProvider('kotlin', {
        provideCompletionItems: () => {
          return { suggestions: getSuggestions(monaco) };
        },
      });

      if (editor) {
        initServices({
          serviceConfig: {
            debugLogging: true,
          },
        }).then(() => {
          monaco.languages.register({
            id: 'kotlin',
          });

          connectToLs();

          editor.focus();
        });
      }
    }
  };

  return (
    <div className="editor">
      <MonacoEditor
        className="content"
        height="100%"
        language={props.language}
        onChange={(value) => handleChange(value)}
        width="100%"
        value={props.value}
        onMount={handleMount}
        options={MONACO_OPTIONS}
      />
    </div>
  );
};

export default Editor;
