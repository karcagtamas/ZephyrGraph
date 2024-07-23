import { useQuery } from '@tanstack/react-query';
import Editor from '../common/Editor';
import { fetchInitial } from '../../services/graph.service';
import Loading from '../common/Loading';

type Props = {
  onChange: (value: string) => void;
};

const GraphCodeEditor: React.FC<Props> = (props: Props) => {
  const { isLoading, data } = useQuery({
    queryKey: ['initialValue'],
    queryFn: () =>
      fetchInitial().then((res) => {
        props.onChange(res);
        return res;
      }),
  });

  if (isLoading || !data) {
    return <Loading />;
  }

  return (
    <Editor
      initialValue={data}
      onChange={props.onChange}
      language="kotlin"
    ></Editor>
  );
};

export default GraphCodeEditor;
