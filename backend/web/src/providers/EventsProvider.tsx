import { EventsContext, PubSub } from '../core/events.context';
import { PropsWithChildren, useState } from 'react';

type Props = PropsWithChildren;

const EventsProvider: React.FC<Props> = (props: Props) => {
  const [handler] = useState(new PubSub());

  return (
    <EventsContext.Provider value={handler}>
      {props.children}
    </EventsContext.Provider>
  );
};

export default EventsProvider;
