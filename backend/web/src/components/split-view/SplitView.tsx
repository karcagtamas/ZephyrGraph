import { useContext, useState } from 'react';
import './SplitView.scss';
import SplitViewHeader from './SplitViewHeader';
import MessageBoard from '../message-board/MessageBoard';
import { MessageType } from '../../models/message';
import GraphCodeEditor from '../editor/GraphCodeEditor';
import { ErrorData } from '../../core/api.helper';
import { LinearProgress } from '@mui/material';
import { useDispatch, useSelector } from 'react-redux';
import { addMessage } from '../../store/messageSlice';
import { EventsContext } from '../../core/events.context';
import { RootState } from '../../store/store';
import { localDateTimeConverter } from '../../core/date.helper';
import { clearWarning, setWarning } from '../../store/warningSlice';
import { updateModel } from '../../store/graphSlice';
import CauseEffectGraph from '../graph/CauseEffectGraph';
import { parseScript } from '../../services/graph.service';
import LogicalPanel from '../logical-panel/LogicalPanel';
import { setLogical } from '../../store/logicalSlice';

type State = {
  isGraphVisible: boolean;
  isLogicalPanelVisible: boolean;
  isMessageBoardVisible: boolean;
  isExecuting: boolean;
};

const SplitView = () => {
  const [state, setState] = useState<State>({
    isMessageBoardVisible: false,
    isGraphVisible: false,
    isLogicalPanelVisible: false,
    isExecuting: false,
  });
  const dispatch = useDispatch();
  const events = useContext(EventsContext);
  const content = useSelector((state: RootState) => state.content.content);

  const handleLogicalPanelToggle = () => {
    setState({ ...state, isLogicalPanelVisible: !state.isLogicalPanelVisible });
  };

  const handleGraphToggle = () => {
    setState({ ...state, isGraphVisible: !state.isGraphVisible });
  };

  const handleBottomToggle = () => {
    setState({ ...state, isMessageBoardVisible: !state.isMessageBoardVisible });
  };

  const handleExecute = () => {
    setState({ ...state, isExecuting: true });
    parseScript({ content: content })
      .then((res) => {
        dispatch(updateModel(res.visual));
        dispatch(setLogical(res.logical));
        dispatch(
          addMessage({
            id: new Date().toISOString(),
            content: 'Code is successfully executed.',
            type: MessageType.EXECUTE,
            date: localDateTimeConverter.to(new Date()),
          })
        );
        dispatch(clearWarning());
      })
      .catch((err: ErrorData) => {
        dispatch(updateModel({ nodes: [], edges: [] }));
        dispatch(
          addMessage({
            id: new Date().toISOString(),
            content: `Error during the code execution: ${err.cause}`,
            type: MessageType.ERROR,
            date: localDateTimeConverter.to(new Date()),
          })
        );
        setState({ ...state, isMessageBoardVisible: true });
        dispatch(
          setWarning('Graph state is invalid because of invalid execution')
        );
      })
      .finally(() => {
        setState({ ...state, isExecuting: false });
      });
  };

  const handleEditorValueChange = (newValue: string) => {
    if (content !== newValue) {
      dispatch(setWarning('Graph state is obsolete. Please run execution.'));
    }
  };

  const handleReset = () => {
    events.publish('reset', null);
    dispatch(clearWarning());
  };

  return (
    <div className="frame">
      <SplitViewHeader
        isGraphToggled={state.isGraphVisible}
        onGraphToggle={handleGraphToggle}
        isLogicalPanelToggled={state.isLogicalPanelVisible}
        onLogicalPanelToggle={handleLogicalPanelToggle}
        isBottomToggled={state.isMessageBoardVisible}
        onBottomToggle={handleBottomToggle}
        onExecute={handleExecute}
        onReset={handleReset}
      />
      {state.isExecuting ? <LinearProgress /> : <></>}
      <div className="content">
        <div className="part">
          <GraphCodeEditor onChange={handleEditorValueChange} />
        </div>
        {state.isLogicalPanelVisible ? (
          <div className="part">
            <LogicalPanel />
          </div>
        ) : (
          <></>
        )}
        {state.isGraphVisible ? (
          <div className="part">
            <CauseEffectGraph />
          </div>
        ) : (
          <></>
        )}
      </div>
      {state.isMessageBoardVisible ? (
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
