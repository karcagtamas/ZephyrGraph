import { FC } from 'react';
import MonacoEditor from '@monaco-editor/react';
import './Editor.scss';

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

  return (
    <div className="editor">
      <MonacoEditor
        className="content"
        height="100%"
        defaultLanguage={props.language}
        onChange={(value) => handleChange(value)}
        width="100%"
        value={props.value}
      />
    </div>
  );
};

export default Editor;
