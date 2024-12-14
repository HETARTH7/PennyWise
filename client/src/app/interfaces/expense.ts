export interface Expense {
  amount: number;
  reason: string;
  date: string;
  category: string | null;
  modeOfPayment: string | null;
}
