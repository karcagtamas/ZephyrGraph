import { ReactFlowProvider } from 'reactflow';
import { GraphModel, toFlow } from '../../core/graph/graph.model';
import GraphContent from './GraphContent';
import './Graph.scss';

type Props = {
  model: GraphModel;
};

const Graph: React.FC<Props> = (props: Props) => {
  const [nodes, edges] = toFlow(props.model);

  return (
    <div className="graph-frame">
      <ReactFlowProvider>
        <GraphContent nodes={nodes} edges={edges} />
      </ReactFlowProvider>
    </div>
  );
};

export default Graph;
