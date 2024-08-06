import { FC } from 'react';
import MonacoEditor, { OnMount } from '@monaco-editor/react';
import './Editor.scss';
import { getSuggestions } from '../../core/graph.helper';

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

  const handleMount: OnMount = (_editor, monaco) => {
    if (monaco) {
      monaco.languages.registerCompletionItemProvider('kotlin', {
        provideCompletionItems: (_editor, monaco) => {
          return { suggestions: getSuggestions(monaco) };
        },
      });
    }
  };

  return (
    <div className="editor">
      <MonacoEditor
        className="content"
        height="100%"
        defaultLanguage={props.language}
        onChange={(value) => handleChange(value)}
        width="100%"
        value={props.value}
        onMount={handleMount}
      />
    </div>
  );
};

export default Editor;
