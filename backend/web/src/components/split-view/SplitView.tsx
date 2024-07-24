import { useState } from 'react';
import { GraphModel } from '../../models/graph.model';
import Graph from '../graph/Graph';
import './SplitView.scss';
import SplitViewHeader from './SplitViewHeader';
import MessageBoard from '../message-board/MessageBoard';
import { Message, MessageType } from '../../models/message';
import GraphCodeEditor from '../editor/GraphCodeEditor';
import { parseScript } from '../../services/graph.service';
import { ErrorData } from '../../core/api.helper';
import { LinearProgress } from '@mui/material';

const SplitView = () => {
  const [isGraphVisible, setIsGraphVisible] = useState(false);
  const [isBottomVisible, setIsBottomVisible] = useState(false);
  const [value, setValue] = useState<string>('');
  const [messages, setMessages] = useState<Message[]>([]);
  const [model, setModel] = useState<GraphModel>({ nodes: [], edges: [] });
  const [warning, setWarning] = useState<string>();
  const [executing, setExecuting] = useState(false);

  const handleGraphToggle = () => {
    setIsGraphVisible(!isGraphVisible);
  };

  const handleBottomToggle = () => {
    setIsBottomVisible(!isBottomVisible);
  };

  const handleExecute = () => {
    setExecuting(true);
    parseScript({ content: value })
      .then((res) => {
        setModel(res);
        setMessages([
          ...messages,
          {
            id: new Date().toISOString(),
            content: 'Code is successfully executed.',
            type: MessageType.EXECUTE,
            date: new Date(),
          },
        ]);
      })
      .catch((err: ErrorData) => {
        setModel({ nodes: [], edges: [] });
        setMessages([
          ...messages,
          {
            id: new Date().toISOString(),
            content: `Error during the code execution: ${err.cause}`,
            type: MessageType.ERROR,
            date: new Date(),
          },
        ]);
        setIsBottomVisible(true);
      })
      .finally(() => {
        setWarning(undefined);
        setExecuting(false);
      });
  };

  const handleEditorValueChange = (newValue: string) => {
    setValue(newValue);
    setWarning('Graph state is obsolete. Please run execution.');
  };

  const handleReset = () => {
    // TODO: Invoke event REDUX
  };

  return (
    <div className="frame">
      <SplitViewHeader
        isGraphToggled={isGraphVisible}
        onGraphToggle={handleGraphToggle}
        onExecute={handleExecute}
        isBottomToggled={isBottomVisible}
        onBottomToggle={handleBottomToggle}
        warning={warning}
        onReset={handleReset}
      />
      {executing ? <LinearProgress /> : <></>}
      <div className="content">
        <div className="part">
          <GraphCodeEditor onChange={handleEditorValueChange} />
        </div>
        {isGraphVisible ? (
          <div className="part">
            <Graph model={model} />
          </div>
        ) : (
          <></>
        )}
      </div>
      {isBottomVisible ? (
        <div className="bottom-bar">
          <MessageBoard messages={messages} />
        </div>
      ) : (
        <></>
      )}
    </div>
  );
};

export default SplitView;
