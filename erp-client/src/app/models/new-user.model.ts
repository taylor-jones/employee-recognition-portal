import {RecoveryQuestion} from './recovery.model';

export class NewUser {
  email: string;
  username: string;
  password: string;
  rePassword: string;
  recoveryQuestions: RecoveryQuestion[];
  signature: string;
}
