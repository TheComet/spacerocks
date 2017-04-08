defmodule GameList do
  @derive [Poison.Encoder]
  defstruct [:games]
end
