import { RecoveryQuestion } from './recovery.model';

export class User {
  id: number;
  email: string;
  username: string;
  password: string;
  signature: string;
  isAdmin: boolean;
  isEnabled: boolean;
}

export class UserWithRecoveryQuestions extends User {
  questions: RecoveryQuestion[]
}
