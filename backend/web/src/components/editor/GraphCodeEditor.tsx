import { useQuery } from '@tanstack/react-query';
import Editor from '../common/Editor';
import { fetchInitial } from '../../services/graph.service';
import Loading from '../common/Loading';
import { useContext, useEffect, useState } from 'react';
import { EventsContext } from '../../core/events.context';
import { useDispatch } from 'react-redux';
import { setContent } from '../../store/contentSlice';

type Props = {
  onChange: (value: string) => void;
};

const GraphCodeEditor: React.FC<Props> = (props: Props) => {
  const [initialContent, setInitialContent] = useState('// this is a comment');
  const dispatch = useDispatch();
  const { isLoading, data } = useQuery({
    queryKey: ['initialValue'],
    queryFn: () =>
      fetchInitial().then((res) => {
        setInitialContent(res);
        return res;
      }),
  });
  const events = useContext(EventsContext);

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

  return <Editor value={initialContent} onChange={handleOnChange}></Editor>;
};

export default GraphCodeEditor;
