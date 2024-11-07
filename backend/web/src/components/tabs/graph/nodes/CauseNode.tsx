import { Handle, Position } from 'reactflow';
import { NodeMeta } from '../../../../models/graph.model';
import './CauseNode.scss';
import NodeDescription from './NodeDescription';
import { Typography } from '@mui/material';

type Props = {
  id: string;
  data: { label: string; meta: NodeMeta };
};

const CauseNode: React.FC<Props> = (props: Props) => {
  return (
    <div className="node cause-node">
      <div className="label">
        <Typography fontWeight="bold">{props.data.label}</Typography>
      </div>
      <div className="expression">
        <Typography variant="body2">{props.data.meta.expression}</Typography>
      </div>
      <NodeDescription content={props.data.meta.description} />

      <Handle type="source" position={Position.Right} />
    </div>
  );
};

export default CauseNode;
