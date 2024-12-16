export interface Expense {
  expenseId: number;
  amount: number;
  reason: string;
  date: string;
  category: string | null;
  modeOfPayment: string | null;
}
