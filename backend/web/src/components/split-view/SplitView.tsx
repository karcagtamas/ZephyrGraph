import { useContext, useState } from 'react';
import { GraphModel } from '../../models/graph.model';
import Graph from '../graph/Graph';
import './SplitView.scss';
import SplitViewHeader from './SplitViewHeader';
import MessageBoard from '../message-board/MessageBoard';
import { MessageType } from '../../models/message';
import GraphCodeEditor from '../editor/GraphCodeEditor';
import { parseScript } from '../../services/graph.service';
import { ErrorData } from '../../core/api.helper';
import { LinearProgress } from '@mui/material';
import { useDispatch, useSelector } from 'react-redux';
import { addMessage } from '../../store/messageSlice';
import { EventsContext } from '../../core/events.context';
import { RootState } from '../../store/store';
import { localDateTimeConverter } from '../../core/date.helper';

const SplitView = () => {
  const [isGraphVisible, setIsGraphVisible] = useState(false);
  const [isBottomVisible, setIsBottomVisible] = useState(false);
  const [model, setModel] = useState<GraphModel>({ nodes: [], edges: [] });
  const [warning, setWarning] = useState<string>();
  const [executing, setExecuting] = useState(false);
  const dispatch = useDispatch();
  const events = useContext(EventsContext);
  const content = useSelector((state: RootState) => state.content.content);

  const handleGraphToggle = () => {
    setIsGraphVisible(!isGraphVisible);
  };

  const handleBottomToggle = () => {
    setIsBottomVisible(!isBottomVisible);
  };

  const handleExecute = () => {
    setExecuting(true);
    parseScript({ content: content })
      .then((res) => {
        setModel(res);
        dispatch(
          addMessage({
            id: new Date().toISOString(),
            content: 'Code is successfully executed.',
            type: MessageType.EXECUTE,
            date: localDateTimeConverter.to(new Date()),
          })
        );
      })
      .catch((err: ErrorData) => {
        setModel({ nodes: [], edges: [] });
        dispatch(
          addMessage({
            id: new Date().toISOString(),
            content: `Error during the code execution: ${err.cause}`,
            type: MessageType.ERROR,
            date: localDateTimeConverter.to(new Date()),
          })
        );
        setIsBottomVisible(true);
      })
      .finally(() => {
        setWarning(undefined);
        setExecuting(false);
      });
  };

  const handleEditorValueChange = (newValue: string) => {
    if (content !== newValue) {
      setWarning('Graph state is obsolete. Please run execution.');
    }
  };

  const handleReset = () => {
    events.publish('reset', null);
    setWarning(undefined);
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
          <MessageBoard />
        </div>
      ) : (
        <></>
      )}
    </div>
  );
};

export default SplitView;
