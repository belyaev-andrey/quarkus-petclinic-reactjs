import { IPetType, ISelectOption } from '../../types';
import {url, submitForm, reqHeader} from '../../util';

const toSelectOptions = (pettypes: IPetType[]): ISelectOption[] => pettypes.map(pettype => ({ value: pettype.id, name: pettype.name, obj: pettype }));

export default (ownerId: string, petLoaderPromise: Promise<any>): Promise<any> => {
  return Promise.all(
    [fetch(url('/api/pettypes'), reqHeader())
      .then(response => response.json())
      .then(toSelectOptions),
    fetch(url('/api/owner/' + ownerId), reqHeader())
      .then(response => response.json()),
      petLoaderPromise,
    ]
  ).then(results => ({
    pettypes: results[0],
    owner: results[1],
    pet: results[2]
  }));
};
