import type { TextChanges } from '@typefox/monaco-editor-react';
import { MonacoEditorReactComp } from '@typefox/monaco-editor-react';
import * as vscode from 'vscode';
import {
  RegisteredFileSystemProvider,
  RegisteredMemoryFile,
  registerFileSystemOverlay,
} from '@codingame/monaco-vscode-files-service-override';
import { createUserConfig } from '../../core/language-client/config';
import { MonacoEditorLanguageClientWrapper } from 'monaco-editor-wrapper';
import './Editor.scss';
import { useMemo, useState } from 'react';
import { buildWorkerDefinition } from 'monaco-editor-workers';

const WORKSPACE = import.meta.env.VITE_WORKSPACE;
const WORKSPACE_FILE = import.meta.env.VITE_WORKSPACE_FILE;

buildWorkerDefinition(
  '../../../node_modules/monaco-editor-workers/dist/workers',
  import.meta.url,
  true
);

type Props = {
  onChange: (value: string) => void;
  value: string | undefined;
};

const Editor: React.FC<Props> = (props: Props) => {
  const [fileSystemProvider] = useState(() => {
    const provider = new RegisteredFileSystemProvider(false);
    provider.registerFile(
      new RegisteredMemoryFile(
        vscode.Uri.file(WORKSPACE_FILE),
        props.value ?? '// This is a comment'
      )
    );
    registerFileSystemOverlay(1, provider);
    return provider;
  });

  const onTextChanged = (textChanges: TextChanges) => {
    console.log(
      `Dirty? ${textChanges.isDirty}\ntext: ${textChanges.main}\ntextOriginal: ${textChanges.original}`
    );
    props.onChange(textChanges.main);
  };

  console.log('ALMA');

  const config = useMemo(() => {
    return createUserConfig(
      WORKSPACE,
      props.value ?? '// This is a comment',
      WORKSPACE_FILE
    );
  }, []);

  const editor = useMemo(() => {
    return (
      <MonacoEditorReactComp
        style={{ height: '100%' }}
        userConfig={config}
        onTextChanged={onTextChanged}
        onLoad={(wrapper: MonacoEditorLanguageClientWrapper) => {
          console.log(`Loaded ${wrapper.reportStatus().join('\n').toString()}`);
        }}
        onError={(e) => {
          console.error(e);
        }}
      />
    );
  }, [config]);

  return editor;
};

export default Editor;
