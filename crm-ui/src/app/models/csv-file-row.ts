import { CSVFileColumn } from "./csv-file-column";

export interface CSVFileRow {
    columns: CSVFileColumn[];
    errors: string[];
    rowNum: number;
    status: string;
}