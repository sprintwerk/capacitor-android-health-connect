export interface AndroidHealthConnectPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
