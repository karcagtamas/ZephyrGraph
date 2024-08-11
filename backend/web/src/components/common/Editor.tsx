import { FC, useCallback, useEffect, useMemo, useRef, useState } from 'react';
import './Editor.scss';
import {
  MonacoEditorReactComp,
  TextChanges,
} from '@typefox/monaco-editor-react';
import { UserConfig } from 'monaco-editor-wrapper';
import { buildWorkerDefinition } from 'monaco-editor-workers';
import { BASE_CONFIG } from '../../core/constants';

buildWorkerDefinition(
  '../../../node_modules/monaco-editor-workers/dist/workers',
  import.meta.url,
  false
);

type Props = {
  onChange: (value: string) => void;
  language: string;
  value: string | undefined;
};

const baseConfig: UserConfig = BASE_CONFIG;

const Editor: FC<Props> = (props: Props) => {
  const [config, setConfig] = useState(baseConfig);
  const editorRef = useRef(null);

  useEffect(() => {
    const newConfig = { ...config };

    if (newConfig.wrapperConfig.editorAppConfig.codeResources?.main) {
      newConfig.wrapperConfig.editorAppConfig.codeResources.main.text =
        props.value ?? '';
    }

    setConfig(newConfig);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const handleChange = useCallback(
    (changes: TextChanges) => {
      props.onChange(changes.main);
    },
    // eslint-disable-next-line react-hooks/exhaustive-deps
    [props.onChange]
  );

  const editor = useMemo(() => {
    return (
      <MonacoEditorReactComp
        ref={editorRef}
        className="content"
        style={{ height: '100%' }}
        userConfig={baseConfig}
        onTextChanged={handleChange}
      />
    );
  }, [handleChange]);

  return <div className="editor">{editor}</div>;
};

export default Editor;
