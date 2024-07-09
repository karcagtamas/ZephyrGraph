import { CauseMeta, EffectMeta, NodeMeta } from './meta.model';
import { Node as FlowNode, Edge as FlowEdge } from 'reactflow';

export interface GraphModel {
  nodes: Node[];
  edges: Edge[];
}

export class Node {
  meta: NodeMeta;

  constructor(meta: NodeMeta) {
    this.meta = meta;
  }

  getType(): string | undefined {
    if (this.meta instanceof CauseMeta) {
      return 'input';
    } else if (this.meta instanceof EffectMeta) {
      return 'output';
    }

    return undefined;
  }
}

export class Edge {
  from: NodeMeta;
  to: NodeMeta;

  constructor(from: NodeMeta, to: NodeMeta) {
    this.from = from;
    this.to = to;
  }

  public get id(): string {
    return `${this.from.id}->${this.to.id}`;
  }
}

export const toFlow = (model: GraphModel): [FlowNode[], FlowEdge[]] => {
  return [
    model.nodes.map((node) => ({
      id: node.meta.id,
      type: node.getType(),
      position: { x: 0, y: 0 },
      data: { label: node.meta.displayName },
    })),
    model.edges.map((edge) => ({
      id: edge.id,
      source: edge.from.id,
      target: edge.to.id,
      type: 'straight',
    })),
  ];
};
