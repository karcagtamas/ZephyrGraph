import { NodeMeta } from './meta.model';
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
    model.nodes.map((node, index) => ({
      id: node.meta.id,
      position: { x: index * 200, y: index * 60 },
      data: { label: node.meta.displayName },
    })),
    model.edges.map((edge) => ({
      id: edge.id,
      source: edge.from.id,
      target: edge.to.id,
    })),
  ];
};
