import { Employee } from '../models/employee.model';
import { AwardType } from '../models/awardType.model';
import { User } from './user.model';

export class Award {
  id: number;
  awardType: AwardType;
  employee: Employee;
  user: User;
  description: string;
  awardedDate: string;
  awardedTime: string;
}
