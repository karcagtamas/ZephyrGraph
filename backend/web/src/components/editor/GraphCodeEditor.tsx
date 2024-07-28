import { useQuery } from '@tanstack/react-query';
import Editor from '../common/Editor';
import { fetchInitial } from '../../services/graph.service';
import Loading from '../common/Loading';
import { useContext, useEffect } from 'react';
import { EventsContext } from '../../core/events.context';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../store/store';
import { setContent } from '../../store/contentSlice';

type Props = {
  onChange: (value: string) => void;
};

const GraphCodeEditor: React.FC<Props> = (props: Props) => {
  const dispatch = useDispatch();
  const { isLoading, data } = useQuery({
    queryKey: ['initialValue'],
    queryFn: () => fetchInitial(),
  });
  const events = useContext(EventsContext);
  const content = useSelector((state: RootState) => state.content.content);

  useEffect(() => {
    if (data) {
      dispatch(setContent(data));
    }
    const callback = events.subscribe('reset', () => {
      if (data) {
        dispatch(setContent(data));
      }
    });

    return () => {
      events.unsubscribe('reset', callback);
    };
  }, [events, data, dispatch]);

  if (isLoading || !data) {
    return <Loading />;
  }

  const handleOnChange = (value: string) => {
    dispatch(setContent(value));
    props.onChange(value);
  };

  return (
    <Editor
      value={content}
      onChange={handleOnChange}
      language="kotlin"
    ></Editor>
  );
};

export default GraphCodeEditor;
