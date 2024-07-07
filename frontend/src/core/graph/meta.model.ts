export enum Action {
  AND,
  OR,
}

export abstract class NodeMeta {
  id: string;
  displayName: string;

  constructor(id: string, displayName: string) {
    this.id = id;
    this.displayName = displayName;
  }
}

export class CauseMeta extends NodeMeta {
  constructor(id: string, displayName: string) {
    super(id, displayName);
  }
}

export class EffectMeta extends NodeMeta {
  constructor(id: string, displayName: string) {
    super(id, displayName);
  }
}

export class ActionMeta extends NodeMeta {
  action: Action;

  constructor(id: string, displayName: string, action: Action) {
    super(id, displayName);

    this.action = action;
  }
}
