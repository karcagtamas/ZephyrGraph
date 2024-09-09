import { Node as FlowNode, Edge as FlowEdge } from 'reactflow';

export type NodeType = 'CAUSE' | 'ACTION' | 'EFFECT';

export enum Action {
  AND,
  OR,
  NOT,
}

export interface NodeMeta {
  type: NodeType;
  definition?: {
    expression?: string;
    statement?: string;
  };
  description?: string;
}

export class CauseMeta implements NodeMeta {
  type: NodeType = 'CAUSE';
}

export class EffectMeta implements NodeMeta {
  type: NodeType = 'EFFECT';
}

export class ActionMeta implements NodeMeta {
  type: NodeType = 'ACTION';
  action: Action;

  constructor(action: Action) {
    this.action = action;
  }
}

export class Node {
  id: string;
  displayName: string;
  meta: NodeMeta;

  constructor(id: string, displayName: string, meta: NodeMeta) {
    this.id = id;
    this.displayName = displayName;
    this.meta = meta;
  }

  static getType(node: Node): string | undefined {
    if (node.meta.type === 'CAUSE') {
      return 'CauseNode';
    } else if (node.meta.type === 'EFFECT') {
      return 'EffectNode';
    } else if (node.meta.type === 'ACTION') {
      return 'ActionNode';
    }

    return undefined;
  }
}

export interface Edge {
  id: string;
  source: Node;
  target: Node;
}

export interface GraphModel {
  nodes: Node[];
  edges: Edge[];
}

export const toFlow = (model: GraphModel): [FlowNode[], FlowEdge[]] => {
  return [
    model.nodes.map((node) => ({
      id: node.id,
      type: Node.getType(node),
      position: { x: 0, y: 0 },
      data: { label: node.displayName, meta: node.meta },
    })),
    model.edges.map((edge) => ({
      id: edge.id,
      source: edge.source.id,
      target: edge.target.id,
      type: 'straight',
    })),
  ];
};
