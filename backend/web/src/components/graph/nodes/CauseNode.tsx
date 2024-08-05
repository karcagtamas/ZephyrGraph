import { Handle, Position } from 'reactflow';
import { NodeMeta } from '../../../models/graph.model';
import './CauseNode.scss';
import NodeDescription from './NodeDescription';

type Props = {
  id: string;
  data: { label: string; meta: NodeMeta };
};

const CauseNode: React.FC<Props> = (props: Props) => {
  return (
    <div className="node cause-node">
      <div className="label">{props.data.label}</div>
      <div className="expression">{props.data.meta.definition?.expression}</div>
      <NodeDescription content={props.data.meta.description} />

      <Handle type="source" position={Position.Right} />
    </div>
  );
};

export default CauseNode;
