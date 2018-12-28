const nameSpaces: string[] = [];

const createNamespacedActions = (namespace: string): any => {
  if (nameSpaces.includes(namespace)) {
    throw new Error('Not unique namespace');
  }

  nameSpaces.push(namespace);

  return (action: string): string => `${namespace}_${action}`;
};

export default createNamespacedActions;
