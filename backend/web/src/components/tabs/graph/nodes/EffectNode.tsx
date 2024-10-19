import { Handle, Position } from 'reactflow';
import { NodeMeta } from '../../../../models/graph.model';
import './EffectNode.scss';
import NodeDescription from './NodeDescription';

type Props = {
  id: string;
  data: { label: string; meta: NodeMeta };
};

const EffectNode: React.FC<Props> = (props: Props) => {
  return (
    <div className="node effect-node">
      <div className="label">{props.data.label}</div>
      <NodeDescription content={props.data.meta.description} />

      <Handle type="target" position={Position.Left} />
    </div>
  );
};

export default EffectNode;
