import { useState } from 'react';
import { GraphModel } from '../../models/graph.model';
import Editor from '../editor/Editor';
import Graph from '../graph/Graph';
import './SplitView.scss';
import SplitViewHeader from './SplitViewHeader';
import MessageBoard from '../message-board/MessageBoard';
import { Message, MessageType } from '../../models/message';
import { useQuery } from '@tanstack/react-query';
import { fetchDummyExample } from '../../services/graph.service';
import Loading from '../common/Loading';

const SplitView = () => {
  const [isGraphVisible, setIsGraphVisible] = useState(false);
  const [isBottomVisible, setIsBottomVisible] = useState(false);
  const [value, setValue] = useState<string | null>(null);
  const [messages, setMessages] = useState<Message[]>([]);
  const { isLoading, data } = useQuery({
    queryKey: ['example'],
    queryFn: () => fetchDummyExample().then((res) => res.data),
  });

  const initialEditorValue: string | null = null;

  if (isLoading || !data) {
    return <Loading />;
  }

  const model: GraphModel = data;

  const handleGraphToggle = () => {
    setIsGraphVisible(!isGraphVisible);
  };

  const handleBottomToggle = () => {
    setIsBottomVisible(!isBottomVisible);
  };

  const handleExecute = () => {
    setMessages([
      ...messages,
      {
        id: new Date().toISOString(),
        content: 'Code is executed',
        type: MessageType.EXECUTE,
        date: new Date(),
      },
    ]);
  };

  const handleEditorValueChange = (newValue: string) => {
    setValue(newValue);

    console.log(value);
  };

  return (
    <div className="frame">
      <SplitViewHeader
        isGraphToggled={isGraphVisible}
        onGraphToggle={handleGraphToggle}
        onExecute={handleExecute}
        isBottomToggled={isBottomVisible}
        onBottomToggle={handleBottomToggle}
      />
      <div className="content">
        <div className="part">
          <Editor
            initialValue={initialEditorValue}
            onChange={handleEditorValueChange}
          />
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
