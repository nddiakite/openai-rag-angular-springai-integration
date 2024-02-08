export function initializer(): () => Promise<any> {
  return (): Promise<any> => {
    return new Promise(async (resolve, reject) => {
      try {
        resolve(resolve);
      } catch (error) {
        reject(error);
      }
    });
  };
}
