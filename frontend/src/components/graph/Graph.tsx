import ReactFlow, {
  Background,
  BackgroundVariant,
  Controls,
  MiniMap,
} from 'reactflow';
import { GraphModel, toFlow } from '../../core/graph/graph.model';
import './Graph.scss';
import 'reactflow/dist/style.css';

type Props = {
  model: GraphModel;
};

const Graph: React.FC<Props> = (props: Props) => {
  const [nodes, edges] = toFlow(props.model);

  return (
    <div className="graph-frame">
      <ReactFlow nodes={nodes} edges={edges}>
        <Controls />
        <MiniMap />
        <Background variant={BackgroundVariant.Dots} gap={12} size={1} />
      </ReactFlow>
    </div>
  );
};

export default Graph;
