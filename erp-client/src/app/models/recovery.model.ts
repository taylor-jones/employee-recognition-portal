export class RecoveryQuestion {
  id: number;
  question: string;
  answer: string;
}

export class PasswordReset {
  username: string;
  newPassword: string;
  reNewPassword: string;
}
