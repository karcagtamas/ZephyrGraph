import { Handle, Position } from 'reactflow';
import { NodeMeta } from '../../../../models/graph.model';
import './ActionNode.scss';
import NodeDescription from './NodeDescription';
import { Typography } from '@mui/material';

type Props = {
  id: string;
  data: { label: string; meta: NodeMeta };
};

const ActionNode: React.FC<Props> = (props: Props) => {
  return (
    <div className="node action-node">
      <div className="label">
        <Typography fontWeight="bold">{props.data.label}</Typography>
      </div>
      <NodeDescription content={props.data.meta.description} />

      <Handle type="source" position={Position.Right} />
      <Handle type="target" position={Position.Left} />
    </div>
  );
};

export default ActionNode;
