import { CSVFileRow } from "./csv-file-row";

export interface CSVFileDetails {
    columns: number;
    loadableRows: number;
    matchedColumnHeaders: string[];
    rowErrors: number;
    records: CSVFileRow[];
    rows: number;
    unmatchedCSVHeaders: string[];
    unmatchedObjectHeaders: string[];
}
  