import { createContext } from 'react';

export class PubSub {
  private subscribers: Record<string, ((data: unknown) => void)[]>;

  constructor() {
    this.subscribers = {};
  }

  subscribe(
    event: string,
    callback: (data: unknown) => void
  ): (data: unknown) => void {
    if (!this.subscribers[event]) {
      this.subscribers[event] = [];
    }

    this.subscribers[event].push(callback);

    return callback;
  }

  publish(event: string, data: unknown) {
    if (this.subscribers[event]) {
      this.subscribers[event].forEach((callback) => callback(data));
    }
  }

  unsubscribe(event: string, callback: (data: unknown) => void) {
    if (this.subscribers[event]) {
      this.subscribers[event] = this.subscribers[event].filter(
        (cb) => cb !== callback
      );
    }
  }
}

export const EventsContext = createContext<PubSub>(new PubSub());
