import { FC, useState } from 'react';
import MonacoEditor from '@monaco-editor/react';
import './Editor.scss';

type Props = {
  initialValue: string | null;
  onChange: (value: string) => void;
  language: string;
};

const Editor: FC<Props> = (props: Props) => {
  const [value, setValue] = useState(
    props.initialValue ?? '// Write some code'
  );

  const handleChange = (value: string | undefined) => {
    if (value) {
      setValue(value);
      props.onChange(value);
    }
  };

  return (
    <div className="editor">
      <MonacoEditor
        className="content"
        height="100%"
        defaultLanguage={props.language}
        defaultValue={value}
        onChange={(value) => handleChange(value)}
        width="100%"
      />
    </div>
  );
};

export default Editor;
